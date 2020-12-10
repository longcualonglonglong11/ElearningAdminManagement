import ScriptTag from "react-script-tag";
export default function () {
  return (
    <div className="script-component">
      <ScriptTag
        isHydrating={false}
        src={"../../assets/plugins/jquery/js/jquery.min.js"}
      />
      <ScriptTag
        isHydrating={false}
        src={"../../assets/plugins/jquery-ui/js/jquery-ui.min.js"}
      />
      <ScriptTag
        isHydrating={false}
        src="//cdn.ckeditor.com/4.11.4/full/ckeditor.js"
      />
      <ScriptTag
        isHydrating={false}
        src="http://cdn.ckeditor.com/4.4.7/standard-all/adapters/jquery.js"
      />

      <ScriptTag
        isHydrating={false}
        src={"../../assets/plugins/modernizr/js/modernizr.js"}
      />

      <ScriptTag
        isHydrating={false}
        src={
          // "../../assets/plugins/popper.js/js/popper.min.js"

          "../../assets/plugins/popper.js/js/popper.min.js"
        }
      />

      <ScriptTag
        isHydrating={false}
        src={"../../assets/plugins/bootstrap/js/bootstrap.min.js"}
      />

      <ScriptTag
        isHydrating={false}
        src={"../../assets/plugins/jquery-slimscroll/js/jquery.slimscroll.js"}
      />

      <ScriptTag isHydrating={false} src={"../../assets/js/pcoded.min.js"} />

      <ScriptTag
        isHydrating={false}
        src={"../../assets/js/vartical-layout.min.js"}
      />

      <ScriptTag
        isHydrating={false}
        src={"../../assets/js/jquery.mCustomScrollbar.concat.min.js"}
      />

      <ScriptTag isHydrating={false} src={"../../assets/js/script.js"} />
      <ScriptTag
        isHydrating={false}
        src={"../../assets/plugins/modernizr/js/css-scrollbars.js"}
      />
    </div>
  );
}






















// Anotherway

// useScript("../../assets/plugins/jquery/js/jquery.min.js");
// useScript(
//   "../../assets/plugins/jquery-ui/js/jquery-ui.min.js"
// );
// useScript(
//   "../../assets/plugins/bootstrap/js/bootstrap.min.js"
// );
// useScript(
//   process.env.PUBLIC_URL +
//     "/assets/plugins/jquery-slimscroll/js/jquery.slimscroll.js"
// );
// useScript(
//   "../../assets/plugins/modernizr/js/css-scrollbars.js"
// );
// useScript(
//   "../../assets/plugins/popper.js/js/popper.min.js"
// );

// useScript("../../assets/js/pcoded.min.js");
// useScript("../../assets/js/vartical-layout.min.js");
// useScript("/assets/js/jquery.mCustomScrollbar.concat.min.js");
// useScript("../../assets/js/script.js");
// useScript("//cdn.ckeditor.com/4.11.4/full/ckeditor.js");
// useScript("http://cdn.ckeditor.com/4.4.7/standard-all/adapters/jquery.js");
// useEffect(() => {

// }, [])
