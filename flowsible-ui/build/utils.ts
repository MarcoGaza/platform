import dayjs from "dayjs";
import { readdir, stat } from "node:fs";
import { fileURLToPath } from "node:url";
import { dirname, resolve } from "node:path";
import { sum, formatBytes } from "@pureadmin/utils";
import {
  name,
  version,
  engines,
  dependencies,
  devDependencies
} from "../package.json";

/** Start`node`Absolute path of the working directory of the process */
const root: string = process.cwd();

/**
 * @description Generate a new absolute path based on the optional path segment
 * @param dir Path segment，Default`build`
 * @param metaUrl Module integrity`url`，If in`build`Call outside the directory must be passed`import.meta.url`
 */
const pathResolve = (dir = ".", metaUrl = import.meta.url) => {
  // Absolute path of the current file directory
  const currentFileDir = dirname(fileURLToPath(metaUrl));
  // build Absolute path of the directory
  const buildDir = resolve(currentFileDir, "build");
  // Resolved absolute path
  const resolvedPath = resolve(currentFileDir, dir);
  // Check whether the resolved absolute path is in build In the directory
  if (resolvedPath.startsWith(buildDir)) {
    // In build In the directory，Return the current file path
    return fileURLToPath(metaUrl);
  }
  // Not in build In the directory，Return the resolved absolute path
  return resolvedPath;
};

/** Set alias */
const alias: Record<string, string> = {
  "@": pathResolve("../src"),
  "@build": pathResolve()
};

/** Name of the platform、Version、Required for running`node`And`pnpm`Version、Dependency、Type hints for the last build time */
const __APP_INFO__ = {
  pkg: { name, version, engines, dependencies, devDependencies },
  lastBuildTime: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss")
};

/** Processing environment variables */
const wrapperEnv = (envConf: Recordable): ViteEnv => {
  // Default value
  const ret: ViteEnv = {
    VITE_PORT: 8848,
    VITE_PUBLIC_PATH: "",
    VITE_ROUTER_HISTORY: "",
    VITE_CDN: false,
    VITE_HIDE_HOME: "false",
    VITE_COMPRESSION: "none"
  };

  for (const envName of Object.keys(envConf)) {
    let realName = envConf[envName].replace(/\\n/g, "\n");
    realName =
      realName === "true" ? true : realName === "false" ? false : realName;

    if (envName === "VITE_PORT") {
      realName = Number(realName);
    }
    ret[envName] = realName;
    if (typeof realName === "string") {
      process.env[envName] = realName;
    } else if (typeof realName === "object") {
      process.env[envName] = JSON.stringify(realName);
    }
  }
  return ret;
};

const fileListTotal: number[] = [];

/** Get the total size of all files in the specified folder */
const getPackageSize = options => {
  const { folder = "dist", callback, format = true } = options;
  readdir(folder, (err, files: string[]) => {
    if (err) throw err;
    let count = 0;
    const checkEnd = () => {
      ++count == files.length &&
        callback(format ? formatBytes(sum(fileListTotal)) : sum(fileListTotal));
    };
    files.forEach((item: string) => {
      stat(`${folder}/${item}`, async (err, stats) => {
        if (err) throw err;
        if (stats.isFile()) {
          fileListTotal.push(stats.size);
          checkEnd();
        } else if (stats.isDirectory()) {
          getPackageSize({
            folder: `${folder}/${item}/`,
            callback: checkEnd
          });
        }
      });
    });
    files.length === 0 && callback(0);
  });
};

export { root, pathResolve, alias, __APP_INFO__, wrapperEnv, getPackageSize };
