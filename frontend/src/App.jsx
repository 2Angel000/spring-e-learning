import {useEffect, useState} from 'react'
import './App.css'
import axios from "axios";
import {API_URL} from "./config.js";

function App() {
    const [saludo, setSaludo]=useState("");

    useEffect(() => {
        fetch("http://localhost:8080/api/hello")
            .then((response) => response.text())
            .then((data) => {
                console.log("Respuesta del backend:", data); // <-- Agrega esto
                setSaludo(data);
            })
            .catch((error) => console.error("Error:", error));
    }, []);


    return (
    <>
        <div>
            <h1>React dice</h1>
            <h2>
                {saludo}
            </h2>
        </div>
    </>
  )
}

export default App
