import React, {ChangeEvent, useState} from "react";
import "./App.css";
import Main from "./view/Main";

export type ChatMessage = {
    role: string;
    content: string;
}



function App(): JSX.Element {
    return (
        <Main/>
    )
}

export default App;
