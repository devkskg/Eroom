package com.eroom.chat.specification;

import org.springframework.data.jpa.domain.Specification;

import com.eroom.chat.entity.ChatMessage;
import com.eroom.chat.entity.Chatroom;

public class ChatMessageSpecification {

	public static Specification<ChatMessage> roomNoEquals(Chatroom chatroom) {
        return (root, query, cb) -> 
            cb.equal(root.get("chatroom").get("chatroomNo"), chatroom.getChatroomNo());
    }
	
}