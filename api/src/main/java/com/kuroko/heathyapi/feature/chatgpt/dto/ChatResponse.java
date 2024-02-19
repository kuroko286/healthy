package com.kuroko.heathyapi.feature.chatgpt.dto;

import java.util.List;

import lombok.Data;

@Data
public class ChatResponse {
    private List<Choice> choices;

    public static class Choice {

        private int index;
        private MessageDto message;

        public Choice(int index, MessageDto message) {
            this.index = index;
            this.message = message;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public MessageDto getMessage() {
            return message;
        }

        public void setMessage(MessageDto message) {
            this.message = message;
        }
    }
}
