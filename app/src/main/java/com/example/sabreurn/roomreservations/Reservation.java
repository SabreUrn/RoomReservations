package com.example.sabreurn.roomreservations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reservation implements Serializable {
	@SerializedName("id")
	private int id;
	@SerializedName("fromTimeString")
	private String fromTimeString;
	@SerializedName("toTimeString")
	private String toTimeString;
	@SerializedName("userId")
	private String userId;
	@SerializedName("purpose")
	private String purpose;
	@SerializedName("roomId")
	private int roomId;


	public Reservation(int id, String fromTimeString, String toTimeString, String userId, String purpose, int roomId) {
		this.id = id;
		this.fromTimeString = fromTimeString;
		this.toTimeString = toTimeString;
		this.userId = userId;
		this.purpose = purpose;
		this.roomId = roomId;
	}
	public Reservation() {}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getFromTimeString() { return fromTimeString; }
	public void setFromTimeString(String fromTimeString) { this.fromTimeString = fromTimeString; }

	public String getToTimeString() { return toTimeString; }
	public void setToTimeString(String toTimeString) { this.toTimeString = toTimeString; }

	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	public String getPurpose() { return purpose; }
	public void setPurpose(String purpose) { this.purpose = purpose; }

	public int getRoomId() { return roomId; }
	public void setRoomId(int roomId) { this.roomId = roomId; }

	@Override
	public String toString() { return "Reservation " + id + " in room " + roomId + "for purpose of: " + purpose; }
}
