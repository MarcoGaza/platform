import NProgress from "nprogress";
import "nprogress/nprogress.css";

NProgress.configure({
  // Animation method
  easing: "ease",
  // Increase the speed of the progress bar
  speed: 500,
  // Whether to display loadingico
  showSpinner: false,
  // Automatically increment interval
  trickleSpeed: 200,
  // Minimum percentage during initialization
  minimum: 0.3
});

export default NProgress;
