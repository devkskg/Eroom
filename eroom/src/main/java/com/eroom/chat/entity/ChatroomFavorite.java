package com.eroom.chat.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="chatroom_favorite")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomFavorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="chatroom_favorite_no")
	private Long chatroomFavoriteNo; // 채팅방 즐겨찾기 번호
	
	// 채팅방번호 FK 연결
	
	@CreationTimestamp
	@Column(updatable=false, name="favorited_reg_date")
	private LocalDateTime favoritedRegDate; // 즐겨찾기 등록일
	
	// 사번 FK 연결
	
	
	
}
