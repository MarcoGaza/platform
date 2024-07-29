export interface ListItem {
  avatar: string;
  title: string;
  datetime: string;
  type: string;
  description: string;
  status?: "primary" | "success" | "warning" | "info" | "danger";
  extra?: string;
}

export interface TabItem {
  key: string;
  name: string;
  list: ListItem[];
  emptyText: string;
}

export const noticesData: TabItem[] = [
  {
    key: "1",
    name: "Notification",
    list: [],
    emptyText: "No Notification"
  },
  {
    key: "2",
    name: "Message",
    list: [
      {
        avatar: "https://xiaoxian521.github.io/hyperlink/svg/smile1.svg",
        title: "Xiao Ming Commented on You",
        description:
          "Sincerity is in the Heart，Trust is in Action，Sincerity is in the Unity of Heart and Action。",
        datetime: "Today",
        type: "2"
      },
      {
        avatar: "https://xiaoxian521.github.io/hyperlink/svg/smile2.svg",
        title: "Li Bai Replied to You",
        description:
          "There Will Be a Time to Ride the Wind and Waves，Set Sail to Cross the Sea。",
        datetime: "Yesterday",
        type: "2"
      },
      {
        avatar: "https://xiaoxian521.github.io/hyperlink/svg/smile5.svg",
        title: "Title",
        description:
          "Please move the mouse here，To test how long messages will be handled here。The maximum number of description lines set in this example is2，Exceeds2The description content of the line will be omitted and can be viewed throughtooltipView the full content",
        datetime: "Time",
        type: "2"
      }
    ],
    emptyText: "No news"
  },
  {
    key: "3",
    name: "To be done",
    list: [
      {
        avatar: "",
        title: "Third-party urgent code changes",
        description:
          "Kobayashi submitted on 2024-05-10，Need to complete the code change task before 2024-05-11 Expires soon",
        datetime: "",
        extra: "*#*732*#*",
        status: "danger",
        type: "3"
      },
      {
        avatar: "",
        title: "Version release",
        description:
          "Assign Xiaoming to 2024-06-18 Complete the update and release before",
        datetime: "",
        extra: "Time consumed 8 Days",
        status: "warning",
        type: "3"
      },
      {
        avatar: "",
        title: "Develop new features",
        description: "Develop multi-tenant management",
        datetime: "",
        extra: "In progress",
        type: "3"
      },
      {
        avatar: "",
        title: "Task name",
        description:
          "Task needs to be started before 2030-10-30 10:00 Start before",
        datetime: "",
        extra: "Not started",
        status: "info",
        type: "3"
      }
    ],
    emptyText: "No tasks to be done"
  }
];
