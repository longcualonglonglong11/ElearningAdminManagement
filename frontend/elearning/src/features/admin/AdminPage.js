import React, { useEffect, useState, useRef } from "react";
import { BrowserRouter as Router, Switch, Route, Link, useHistory } from "react-router-dom";
import NavBar from "./NavBar";
import SideBar from "./SideBar";
import RoleListPage from "./role/RoleListPage";
import UserListPage from "./user/UserListPage";
import Scripts from "./utility/Scripts";
import "./FontApis.css";

// Import CSS module
import "../../assets/plugins/bootstrap/css/bootstrap.min.css";
import "../../assets/plugins/font-awesome/css/font-awesome.min.css";
import "../../assets/icon/themify-icons/themify-icons.css";
import "../../assets/icon/icofont/css/icofont.css";
import "../../assets/icon/feather/css/feather.css";
import "../../assets/css/style.css";
import "../../assets/css/jquery.mCustomScrollbar.css";
import VIdeoListPage from "./video/VIdeoListPage";
import CourseListPage from "./course/CourseListPage";

export default function ({ match }) {
  // const history = useHistory();

  useEffect(() => {
    // history.push('/admin')
    console.log("admin render");
    return () => {};
  }, []);
  return (
    <div className="admin-page">
      {/* <CSSLoader/> */}
      <div id="pcoded" className="pcoded">
        <div className="pcoded-overlay-box"></div>
        <div className="pcoded-container navbar-wrapper">
          <NavBar />
          <div className="pcoded-main-container">
            <div className="pcoded-wrapper">
              <SideBar />
              <Switch>
                <Route
                  path={`${match.path}/role`}
                  render={() => <RoleListPage />}
                />
                <Route path={`${match.path}/user`} component={UserListPage} />
                <Route path={`${match.path}/video`} component={VIdeoListPage} />
                <Route path={`${match.path}/course`} component={CourseListPage} />

              </Switch>
            </div>
          </div>
        </div>
      </div>
      <Scripts />
    </div>
  );
}
