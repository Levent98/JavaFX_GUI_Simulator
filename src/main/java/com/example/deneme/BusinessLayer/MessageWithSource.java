package com.example.deneme.BusinessLayer;

public class MessageWithSource {

        public final byte[] message;
        public final ListeningSerial source;

        public MessageWithSource(byte[] message, ListeningSerial source) {
            this.message = message;
            this.source = source;
    }
}
