import logo from "./logo.svg";
import "./App.css";
import DefauLayout from "./containers/defauLayout";
import { Switch, Route } from "react-router-dom";
import Login from "./pages/login";

function App() {
  return (
    // <div className="App">
    //   <header className="App-header">
    //     <img src={logo} className="App-logo" alt="logo" />
    //     <p>
    //       Edit <code>src/App.js</code> and save to reload.
    //     </p>
    //     <a
    //       className="App-link"
    //       href="https://reactjs.org"
    //       target="_blank"
    //       rel="noopener noreferrer"
    //     >
    //       Learn React
    //     </a>
    //   </header>
    // </div>
    <Switch>
      <Route exact path="/login" name="Login page" component={Login}></Route>
      <Route path="/" name="Home page" component={DefauLayout}></Route>
    </Switch>
  );
}

export default App;
