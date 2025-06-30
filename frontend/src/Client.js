import React, {useState,useEffect} from "react";
import axios from "axios";
// import bootstrap from 'bootstrap';

function Client(){

    const [client, setClient] = useState([]);

    useEffect(() => {
        axios
        .get('/therapy/clients')
        .then(res => {
            console.log(res)
            setClient(res.data)
        })
        .catch(err => {
            console.log(err)
        })

    }, [])


    const handleEdit = (id) => {
        console.log(id);
    }


    // function clients(){
    //     axios.get('/therapy/clients')
    //     .then(res => {
    //         console.log(res)
    //     }
        
    // }

    return(
        <div>
            <h1>This is the client</h1>
            <table class = "table table-striped">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Age</th>
                        <th scope="col">Diagnosis</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        client.map(c => (
                            <tr key = {c.id}>
                                <th scope="row">{c.id}</th>
                                <td>{c.name} </td>
                                <td>{c.age}</td>
                                <td>{c.diagnosis}</td>
                                <td><img onClick = {() => handleEdit(c.id)} src="/Users/nicolemathias/Documents/therapyApp/frontend/src/icons/pen-to-square-solid.svg"></img></td>
                            </tr>
                        ))
                    }
                </tbody>
                
            </table>

            

        </div>
    );
}
export default Client;