import { http } from "@/utils/http";

// Define the type for the data returned by your landing page API
export type LandingPageResult = {
  success: boolean;
  data: {
    // Define the data structure based on your landing page API response
    features: Array<{
      title: string;
      description: string;
      icon: string; // Assuming your API provides icon names
      link: string; // Link to the feature page
    }>;
    testimonials: Array<{
      quote: string;
      author: string;
      company: string;
    }>;
    // ... other data from your landing page API
  };
};

// API call to fetch landing page data
export const getLandingPageData = () => {
  return http.request<LandingPageResult>("get", "/landing-page"); // Replace with your actual API endpoint
};
