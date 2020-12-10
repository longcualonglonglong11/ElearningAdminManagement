import React from "react";
import "./App.css";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
  Link,
} from "react-router-dom";

import AdminPage from "./features/admin/AdminPage";
function App() {
  return (
    <Router>
      <div className="App">
        <Link to="/admin">To admin page</Link>

        <Switch>
          <Route path="/admin" component={AdminPage} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
