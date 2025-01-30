import { useState } from "react";


export default function Counter(){

    const [tc, setCount] = useState(0);

    function incrementTc(by){
        setCount(tc+ by)
    }

    function decrementTc(by){
        if(tc >0){
        setCount(tc- by)
        }
    }

    return (
        <>
            <span className="count"
            style={{fontSize: "75px"}}>{tc}</span>
            
            <Counterbutton by={1} 
                           incrementTcfun = {incrementTc} 
                           decrementTcfun = {decrementTc}/>

            <Counterbutton by={2} incrementTcfun = {incrementTc} decrementTcfun = {decrementTc}/>
            <Counterbutton by={5} incrementTcfun = {incrementTc} decrementTcfun = {decrementTc}/>
        </>
    )

}


function Counterbutton({by, incrementTcfun, decrementTcfun}){

    const [count , setCount] = useState(0);
    function incrementCounter(){
        setCount(count+ by)
        incrementTcfun(by)
    }

    function decrementCounter(){
        if(count >0){
        setCount(count-by)
        decrementTcfun(by)
        }
    }

    return (
        <div>
            <span className="count"
            style={{fontSize: "50px"}}>{count}</span>
            <br></br>
            
          <div className="btncont">
            <button className="decbutton" 
                style={{fontSize: "10px", margin: "10px", padding: "5px"}}
                onClick={decrementCounter}>-{by}</button>
            <button className="incbutton" 
                style={{fontSize: "10px", margin: "10px", padding: "5px"}}
                onClick={incrementCounter}>+{by}</button>
          </div>
        </div>
    )
}
