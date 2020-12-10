export default function () {
  return (
    <div className="Navbar">
     
          <nav className="navbar header-navbar pcoded-header">
            <div className="navbar-wrapper">
              <div className="navbar-logo">
                <a className="mobile-menu" id="mobile-collapse" href="#!">
                  <i className="feather icon-menu"></i>
                </a>
                <a href="#">
                  <img
                    className="img-fluid"
                    src="assets/images/logo.png"
                    alt="Theme-Logo"
                  />
                </a>
                <a className="mobile-options">
                  <i className="feather icon-more-horizontal"></i>
                </a>
              </div>

              <div className="navbar-container container-fluid">
                <ul className="nav-left">
                  <li className="header-search">
                    <div className="main-search morphsearch-search">
                      <div className="input-group">
                        <span className="input-group-addon search-close">
                          <i className="feather icon-x"></i>
                        </span>
                        <input type="text" className="form-control" />
                        <span className="input-group-addon search-btn">
                          <i className="feather icon-search"></i>
                        </span>
                      </div>
                    </div>
                  </li>
                </ul>
                <ul className="nav-right">
                  <li className="header-notification">
                    <div className="dropdown-primary dropdown">
                      <div className="dropdown-toggle" data-toggle="dropdown">
                        <i className="feather icon-bell"></i>
                        <span className="badge bg-c-pink">5</span>
                      </div>
                    </div>
                  </li>
                  <li className="header-notification">
                    <div className="dropdown-primary dropdown">
                      <div
                        className="displayChatbox dropdown-toggle"
                        data-toggle="dropdown"
                      >
                        <i className="feather icon-message-square"></i>
                        <span className="badge bg-c-green">3</span>
                      </div>
                    </div>
                  </li>
                  <li className="user-profile header-notification">
                    <div className="dropdown-primary dropdown">
                      <div className="dropdown-toggle" data-toggle="dropdown">
                        <img
                          src="assets/images/avatar-4.jpg"
                          className="img-radius"
                          alt="User-Profile-Image"
                        />
                        <span>John Doe</span>
                        <i className="feather icon-chevron-down"></i>
                      </div>
                      <ul
                        className="show-notification profile-notification dropdown-menu"
                        data-dropdown-in="fadeIn"
                        data-dropdown-out="fadeOut"
                      >
                        <li>
                          <a href="#!">
                            <i className="feather icon-settings"></i> Settings
                          </a>
                        </li>
                        <li>
                          <a href="user-profile.htm">
                            <i className="feather icon-user"></i> Profile
                          </a>
                        </li>
                        <li>
                          <a href="auth-normal-sign-in.htm">
                            <i className="feather icon-log-out"></i> Logout
                          </a>
                        </li>
                      </ul>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
        </div>
  );
}
