import logo from './logo.svg';
import './App.css';

import { google } from '@ai-sdk/google';
// import { generateText } from 'ai';




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
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
