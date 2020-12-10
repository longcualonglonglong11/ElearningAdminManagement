import { Link } from "react-router-dom";
export default function () {
  return (
    <div className="Sidebar">
      <nav className="pcoded-navbar">
        <div className="pcoded-inner-navbar main-menu">
          <ul className="pcoded-item pcoded-left-item">
            <li className="">
              <a href="javascript:void(0)">
                <span className="pcoded-micon">
                  <i className="feather icon-home"></i>
                </span>

                <span className="pcoded-mtext">Dashboard</span>
              </a>
            </li>
            <li className="">
              <a href="./category-index.html">
                <span className="pcoded-micon">
                  <i className="feather icon-layers"></i>
                </span>
                <span className="pcoded-mtext">Danh mục</span>
              </a>
            </li>
            <li className="">
              <a href="./course-list.html">
                <span className="pcoded-micon">
                  <i className="feather icon-layers"></i>
                </span>
                <span className="pcoded-mtext">Khóa học</span>
              </a>
            </li>
            <li className="">
              <a href="./video-list.html">
                <span className="pcoded-micon">
                  <i className="feather icon-layers"></i>
                </span>
                <span className="pcoded-mtext">Video</span>
              </a>
            </li>
            <li className="">
              <a href="./target-list.html">
                <span className="pcoded-micon">
                  <i className="feather icon-layers"></i>
                </span>
                <span className="pcoded-mtext">Mục tiêu</span>
              </a>
            </li>

            <li className="">
              <Link to="/admin/user">
                  <span className="pcoded-micon">
                    <i className="feather icon-user"></i>
                  </span>
                  <span className="pcoded-mtext">User Management</span>
              </Link>
            </li>
            <li className="">
              <Link to="/admin/role">
                  <span className="pcoded-micon">
                    <i className="feather icon-user"></i>
                  </span>
                  <span className="pcoded-mtext">Role Management</span>
              </Link>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  );
}
