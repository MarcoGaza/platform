import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** Password regular expression（Password format should be8-18Digit、Letter、Any combination of two symbols） */
export const REGEXP_PWD =
  /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\u4E00-\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$/;

/** Login verification */
const loginRules = reactive(<FormRules>{
  password: [
    {
      validator: (rule, value, callback) => {
        if (value === "") {
          callback(new Error("Please enter password"));
        } else if (!REGEXP_PWD.test(value)) {
          callback(
            new Error(
              "Password format should be8-18Digit、Letter、Any combination of two symbols"
            )
          );
        } else {
          callback();
        }
      },
      trigger: "blur"
    }
  ]
});

export { loginRules };
