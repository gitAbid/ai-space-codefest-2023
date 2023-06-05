import React, {useState} from "react";
import {FormEvent} from "react/ts5.0";
import {ChatMessage} from "../App";

function ChatView() {

    const [message, setMessage] = useState<string>("");
    const [chats, setChats] = useState<ChatMessage[]>([]);
    const [isTyping, setIsTyping] = useState<boolean>(false);

    console.log("Chats", chats)

    const chatUpdater = async (e: FormEvent<HTMLFormElement>, message: string): Promise<void> => {
        e.preventDefault();
        console.log("E Value", e.target)
        if (!message) return;
        setIsTyping(true);
        window.scrollTo(0, 1e10);

        let msgs: ChatMessage[] = [...chats];
        msgs.push({role: "user", content: message});
        setChats(msgs);
        let request: ChatMessage = {role: "user", content: message}
        console.log("MSG_LIst", msgs)
        console.log("Message", message)

        setMessage("");

        fetch("http://localhost:8080/chat", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                role: request.role, content: request.content

            }),
        })
            .then((response) => response.json())
            .then((data: ChatMessage) => {
                msgs.push(data);
                setChats(msgs);
                setIsTyping(false);
                window.scrollTo(0, 1e10);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <div>
            <h2>AI Assistant</h2>
            <br/>
            <div>
                {chats && chats.length
                    ? chats.map((chat: ChatMessage, index: number) => (
                        <p key={index} className={chat.role === "user" ? "user_msg" : ""}>
                <span>
                  <b>{chat.role.toUpperCase()}</b>
                </span>
                            <span>:</span>
                            <span>{chat.content}</span>
                        </p>
                    ))
                    : ""}
            </div>

            <div className={isTyping ? "" : "hide"}>
                <p>
                    <i>{isTyping ? "Typing" : ""}</i>
                </p>
            </div>

            <form action="" onSubmit={(e) => chatUpdater(e, message)}>
                <input
                    type="text"
                    name="message"
                    value={message}
                    placeholder="Type a message here and hit Enter..."
                    onChange={(e) => setMessage(e.target.value)}
                />
            </form>
        </div>
    );
}

export default ChatView
