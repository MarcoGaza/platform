import type { Plugin } from "vite";
import { isArray } from "@pureadmin/utils";
import compressPlugin from "vite-plugin-compression";

export const configCompressPlugin = (
  compress: ViteCompression
): Plugin | Plugin[] => {
  if (compress === "none") return null;

  const gz = {
    // Generated compressed package suffix
    ext: ".gz",
    // The volume is greater thanthresholdIt will be compressed
    threshold: 0,
    // Compressed by default.js|mjs|json|css|htmlSuffix file，Set totrue，Compress all files
    filter: () => true,
    // Whether to delete the original file after compression
    deleteOriginFile: false
  };
  const br = {
    ext: ".br",
    algorithm: "brotliCompress",
    threshold: 0,
    filter: () => true,
    deleteOriginFile: false
  };

  const codeList = [
    { k: "gzip", v: gz },
    { k: "brotli", v: br },
    { k: "both", v: [gz, br] }
  ];

  const plugins: Plugin[] = [];

  codeList.forEach(item => {
    if (compress.includes(item.k)) {
      if (compress.includes("clear")) {
        if (isArray(item.v)) {
          item.v.forEach(vItem => {
            plugins.push(
              compressPlugin(Object.assign(vItem, { deleteOriginFile: true }))
            );
          });
        } else {
          plugins.push(
            compressPlugin(Object.assign(item.v, { deleteOriginFile: true }))
          );
        }
      } else {
        if (isArray(item.v)) {
          item.v.forEach(vItem => {
            plugins.push(compressPlugin(vItem));
          });
        } else {
          plugins.push(compressPlugin(item.v));
        }
      }
    }
  });

  return plugins;
};
