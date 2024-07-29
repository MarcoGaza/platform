import forage from "localforage";
import type { LocalForage, ProxyStorage, ExpiresData } from "./types.d";

class StorageProxy implements ProxyStorage {
  protected storage: LocalForage;
  constructor(storageModel) {
    this.storage = storageModel;
    this.storage.config({
      // PreferredIndexedDBAs the first driver，Not supportedIndexedDBWill automatically downgrade tolocalStorage（WebSQLDeprecated，See detailshttps://developer.chrome.com/blog/deprecating-web-sql）
      driver: [this.storage.INDEXEDDB, this.storage.LOCALSTORAGE],
      name: "pure-admin"
    });
  }

  /**
   * @description Save the data of the corresponding key name to the offline warehouse
   * @param k Key name
   * @param v Key value
   * @param m Cache time（Unit`Minutes`，Default`0`Minutes，Permanent cache）
   */
  public async setItem<T>(k: string, v: T, m = 0): Promise<T> {
    return new Promise((resolve, reject) => {
      this.storage
        .setItem(k, {
          data: v,
          expires: m ? new Date().getTime() + m * 60 * 1000 : 0
        })
        .then(value => {
          resolve(value.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * @description Get the value of the corresponding key name from the offline warehouse
   * @param k Key name
   */
  public async getItem<T>(k: string): Promise<T> {
    return new Promise((resolve, reject) => {
      this.storage
        .getItem(k)
        .then((value: ExpiresData<T>) => {
          value && (value.expires > new Date().getTime() || value.expires === 0)
            ? resolve(value.data)
            : resolve(null);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * @description Delete the value of the corresponding key name from the offline warehouse
   * @param k Key name
   */
  public async removeItem(k: string) {
    return new Promise<void>((resolve, reject) => {
      this.storage
        .removeItem(k)
        .then(() => {
          resolve();
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * @description Delete all key names from the offline warehouse，Reset database
   */
  public async clear() {
    return new Promise<void>((resolve, reject) => {
      this.storage
        .clear()
        .then(() => {
          resolve();
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  /**
   * @description Get all in the data warehousekey
   */
  public async keys() {
    return new Promise<string[]>((resolve, reject) => {
      this.storage
        .keys()
        .then(keys => {
          resolve(keys);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}

/**
 * Secondary packaging [localforage](https://localforage.docschina.org/) Support setting expiration time，Provide complete type hints
 */
export const localForage = () => new StorageProxy(forage);
