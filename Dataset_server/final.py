import tensorflow as tf
import numpy as np
import pandas as pd
import json
from flask import Flask, request
import openai
from googleapiclient.discovery import build
import googlesearch
import requests
import nltk 
from keras.preprocessing.text import Tokenizer 
from keras.layers import Input, Embedding, LSTM, Dense, GlobalMaxPooling1D, Flatten
from keras.models import Model
import matplotlib.pyplot as plt

with open('data.json') as database:
  data = json.load(database)

tags = []
inputs = []
responses = {}

for intent in data['intents']:
  responses[intent['tag']]=intent['responses']
  for lines in intent['patterns']:
    inputs.append(lines)
    tags.append(intent['tag'])
data = pd.DataFrame({"patterns":inputs, "tags": tags})
import string

data['patterns'] = data['patterns'].apply(lambda wrd:[ltrs.lower() for ltrs in wrd if ltrs not in string.punctuation])
data['patterns'] = data['patterns'].apply(lambda wrd: ''.join(wrd))
from keras.preprocessing.text import Tokenizer
tokenizer= Tokenizer(num_words=2000)
tokenizer.fit_on_texts(data['patterns'])
train= tokenizer.texts_to_sequences(data['patterns'])

from tensorflow.keras.preprocessing.sequence import pad_sequences
x_train = pad_sequences(train)

from sklearn.preprocessing import LabelEncoder
le = LabelEncoder()
y_train = le.fit_transform(data['tags'])
input_shape = x_train.shape[1]
print(input_shape)
vocabulary=len(tokenizer.word_index)
print("number of unique words : ", vocabulary)
output_length = le.classes_.shape[0]
print("output_length: ", output_length)
i = Input(shape=(input_shape,))
x = Embedding(vocabulary+1, 10)(i)
x = LSTM(10,return_sequences=True)(x)
x = Flatten()(x)
x = Dense(output_length, activation="softmax")(x)
model = Model(i,x)
model.compile(loss="sparse_categorical_crossentropy", optimizer='adam', metrics=['accuracy'])
train = model.fit(x_train, y_train, epochs=500)
plt.plot(train.history['accuracy'], label='training set accuracy')
plt.plot(train.history['loss'], label='trainig set loss')
plt.legend()
import random

app = Flask(__name__)
@app.route("/", methods=["POST"])
def send_message():
    texts_p = []
    message = request.json.get("message")
    print(message)
    prediction_input = message
    prediction_input = [letters.lower() for letters in prediction_input if letters not in string.punctuation]
    prediction_input = ''.join(prediction_input)
    texts_p.append(prediction_input)

    prediction_input = tokenizer.texts_to_sequences(texts_p)
    prediction_input = np.array(prediction_input).reshape(-1)
    prediction_input = pad_sequences([prediction_input], input_shape)

    output = model.predict(prediction_input)
    output = output.argmax()


    response_tag = le.inverse_transform([output])[0]
    chat_res = random.choice(responses[response_tag])
    print(chat_res)
    return chat_res
if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000,debug=True)