import React from "react"
import ChatView from "./ChatView";

function Main() {
    return (
        <>
            <div className={"row"}>
                <div className={"col-sm-12"}>
                    <h1>OnBoarding AI Space</h1>
                    <br/>
                </div>
                <div className={"col-sm-6"}>
                    <ChatView/>
                </div>

                <div className={"col-sm-6"}>
                    <ChatView/>
                </div>
            </div>
        </>
    )
}

export default Main
