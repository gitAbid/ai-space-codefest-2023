import React, { useState } from "react";
import ReactDOM from "react-dom";


function TrackerView() {
    // State with list of all checked item
    const [checked, setChecked] = useState([]);
    const checkList = ["Completed Task 1", "Completed Task 2", "Completed Task 3", "Completed Task 4"];

    // Add/Remove checked item from list
    const handleCheck = (event) => {
        var updatedList = [...checked];
        if (event.target.checked) {
            updatedList = [...checked, event.target.value];
        } else {
            updatedList.splice(checked.indexOf(event.target.value), 1);
        }
        setChecked(updatedList);
    };

    // Generate string of checked items
    const checkedItems = checked.length
        ? checked.reduce((total, item) => {
            return total + ", " + item;
        })
        : "";

    // Return classes based on whether item is checked
    var isChecked = (item) =>
        checked.includes(item) ? "checked-item" : "not-checked-item";

    return (
        <div >
            <h2>Remaining Tasks</h2>
            <br/>
                <div className="list-container">
                    {checkList.map((item, index) => (
                        <div key={index} className={"row"}>
                            <div className={"col-sm-3"}>

                            </div>
                            <div className={"col-sm-2"} >
                                <input value={item} type="checkbox" onChange={handleCheck} />
                            </div>
                            <div className={"col-sm-6"} style={{display:"flex"}} >
                                <span className={isChecked(item)} style={{color:"Wheat"}}>{item}</span>
                            </div>
                        </div>
                    ))}
                </div>
        </div>
    );
}

export default TrackerView
