import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';


import { google } from '@ai-sdk/google';
// import { generateText } from 'ai';

import Client from "./Client.js";



function App() {

  // const model = google('gemini-1.5-pro-latest');


  // const res = () => {
  //   const { text } = generateText({
  //     model: google('gemini-1.5-pro-latest'),
  //     prompt: 'Write a vegetarian lasagna recipe for 4 people.',
  //   });

  //   console.log("txt",text)
  // }

  

  

  return (
    <div className="App">
        
        <Client/>
    </div>
  );
}

export default App;
