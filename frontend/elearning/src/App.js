import React, {useState, useEffect} from "react";
import "./App.css";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
  Link,
  useHistory
} from "react-router-dom";

import AdminPage from "./features/admin/AdminPage";
function App() {
  const [isLoaded, setIsLoaded] = useState(false)
  useEffect(() => {
    setTimeout(() => {
      setIsLoaded(true)

    }, 2000);
  }, [])
  return (
    <Router>
      {/* {  history.push('/admin')} */}
      <div className="App">
        {isLoaded && <Redirect to="/admin"></Redirect>}
        {/* <Link to="/">To admin page</Link> */}
        <Switch>
          <Route path="/admin" component={AdminPage} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
