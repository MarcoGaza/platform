import { removeToken, setToken, type DataInfo } from "./auth";
import { subBefore, getQueryMap } from "@pureadmin/utils";

/**
 * Simplified front-end single sign-on，Write according to actual business，After the platform is started, you can jump to the following link for testing locally http://localhost:8848/#/permission/page/index?username=sso&roles=admin&accessToken=eyJhbGciOiJIUzUxMiJ9.admin
 * Highlight the key points：
 * Judge whether it is single sign-on，If not, return directly without any logic processing，The following is the logic processing after single sign-on
 * 1.Clear local old information；
 * 2.GeturlImportant parameter information in，Then through setToken Save locally；
 * 3.Delete parameters that do not need to be displayed in url Parameters
 * 4.Use window.location.replace Jump to the correct page
 */
(function () {
  // Get url Parameters in
  const params = getQueryMap(location.href) as DataInfo<Date>;
  const must = ["username", "roles", "accessToken"];
  const mustLength = must.length;
  if (Object.keys(params).length !== mustLength) return;

  // url Parameters meet must All values ​​in，It is determined to be a single sign-on，Avoid infinite loop of refreshing the page when not a single sign-on
  let sso = [];
  let start = 0;

  while (start < mustLength) {
    if (Object.keys(params).includes(must[start]) && sso.length <= mustLength) {
      sso.push(must[start]);
    } else {
      sso = [];
    }
    start++;
  }

  if (sso.length === mustLength) {
    // Determined to be a single sign-on

    // Clear local old information
    removeToken();

    // Save new information locally
    setToken(params);

    // Delete parameters that do not need to be displayed url of
    delete params.roles;
    delete params.accessToken;

    const newUrl = `${location.origin}${location.pathname}${subBefore(
      location.hash,
      "?"
    )}?${JSON.stringify(params)
      .replace(/["{}]/g, "")
      .replace(/:/g, "=")
      .replace(/,/g, "&")}`;

    // Replace history items
    window.location.replace(newUrl);
  } else {
    return;
  }
})();
