import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import * as SockJS from 'sockjs-client';
import { Wbschatmessage } from '../common/wbschatmessage';


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  stompClient: Client | null = null; 
  public messageSubject = new Subject<any>;
  private mainUrl = 'http://localhost:8080/api/user';

  constructor(private HttpClient: HttpClient) { }


  connect(username: String){
    const socket = new SockJS('http://localhost:8080/ws'); 
    console.log('tienanhnday')
    // kết nối websocket từ frontend tới backend
    this.stompClient = new Client({
      webSocketFactory: () => socket,  // Use SockJS as the WebSocket factory
      reconnectDelay: 5000,  // Reconnect delay if connection is lost
      debug: (str) => console.log(str)  // Log STOMP debug messages for troubleshooting
    });

        // kết nối xong lập tức
        this.stompClient.onConnect = (frame) => {
          console.log('Connected to WebSocket server');

          this.stompClient?.subscribe(`/user/${username}/queue/messages`, (message:Message) => {
            console.log('hanoiiii')
            this.messageSubject.next(JSON.parse(message.body))
          });
    
        };

        this.stompClient.onStompError = (frame) => {
          console.error('Broker reported error: ' + frame.headers['message']);  // Log the error message
          console.error('Additional details: ' + frame.body);  // Log additional error details
        };
        
        this.stompClient?.activate();

  }

  sendMessage(username:string, recipient:string, content:string){
    if (this.stompClient && this.stompClient.connected) {
      // Create a chat message object
      const chatMessage = { sender: username, content: content, recipient: recipient };

      // Log the message being sent and the sender
      console.log(`Message sent by ${username}: ${content}`);

      // Publish (send) the message to the '/app/chat.sendMessage' destination
      this.stompClient.publish({
        destination: '/app/chat.sendMessage',
        body: JSON.stringify(chatMessage)  // Convert the message to JSON and send
      });
    } else {
      // Log an error if the WebSocket connection is not active
      console.error('WebSocket is not connected. Unable to send message.');
    }

  }

  getOldMessages(senderId:number, recipentId:number): Observable<Wbschatmessage[]> {
    
    return this.HttpClient.get<Wbschatmessage[]>(`${this.mainUrl}/${senderId}/${recipentId}`);
    
  }

}
