export default function () {
  return (
    <div className="Role-list">
      <div class="pcoded-content">
        <div class="pcoded-inner-content">
          {/* <!-- Main-body start --> */}
          <div class="main-body">
            <div class="page-body">
              <div class="row">
                <div class="col-md-12 mb-2">
                  <div class="page-header-breadcrumb">
                    <ul class="breadcrumb-title">
                      <li class="breadcrumb-item">
                        <a href="/admin">
                          {" "}
                          <i class="feather icon-home"></i> Trang chủ
                        </a>
                      </li>
                      <li class="breadcrumb-item">
                        <a href="#!">Danh sách tài khoản</a>
                      </li>
                    </ul>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="card px-3">
                    <div class="card-header px-0 pb-2">
                      <h2 class="text-uppercase text-center">
                        Danh sách tài khoản
                      </h2>
                      <a href="user-add.html" class="btn btn-sm btn-primary">
                        Thêm mới
                      </a>
                    </div>
                    <div class="card-block table-border-style">
                      <div class="table-responsive">
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>STT</th>
                              <th>Họ Tên</th>
                              <th>Email</th>
                              <th>Số ĐT</th>
                              <th>#</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <th>1</th>
                              <td>Nguyễn Văn Tèo</td>
                              <td>teonguyen@gmail.com</td>
                              <td>01234567899</td>
                              <td>
                                <a
                                  href="#"
                                  class="btn btn-sm btn-info btn-round py-1 font-weight-bold"
                                >
                                  Sửa
                                </a>
                                <a
                                  href="#"
                                  class="btn btn-sm btn-danger btn-round py-1 font-weight-bold"
                                >
                                  Xóa
                                </a>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
