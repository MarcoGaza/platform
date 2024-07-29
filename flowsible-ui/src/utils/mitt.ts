import type { Emitter } from "mitt";
import mitt from "mitt";

/** Global public events need to add types here */
type Events = {
  openPanel: string;
  tagViewsChange: string;
  tagViewsShowModel: string;
  logoChange: boolean;
  changLayoutRoute: string;
};

export const emitter: Emitter<Events> = mitt<Events>();
