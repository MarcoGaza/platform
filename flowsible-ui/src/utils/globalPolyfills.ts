// if the project appears `global is not defined` error，may be a problem with your introduction of a library，for example aws-sdk-js https://github.com/aws/aws-sdk-js
// the solution is to import the file src/main.ts that's it import "@/utils/globalPolyfills";
if (typeof (window as any).global === "undefined") {
  (window as any).global = window;
}

export {};
