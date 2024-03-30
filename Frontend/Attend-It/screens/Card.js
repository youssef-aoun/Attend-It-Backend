import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Alert,
  Image,
} from "react-native";
import moment from "moment";
import { FlatList } from "react-native-gesture-handler";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { upcoming_events_pageable_api, events_images } from "../apis/events";
import AsyncStorage from "@react-native-async-storage/async-storage";

const Card = () => {
  const [events_list, setEvents] = useState([]);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const token = await AsyncStorage.getItem("token");
        const axiosInstance = axios.create({
          headers: {
            Authorization: `Bearer ${token}`, // Include the token in the 'Authorization' header
          },
        });
        // Replace 'events_pageable_api' with your actual backend API URL
        const response = await axios.get(upcoming_events_pageable_api);

        setEvents(response.data);
      } catch (error) {
        console.error("Error fetching events:", error);
      }
    };

    fetchEvents();
  }, []);

  const formattedDate = (item) => {
    // Assuming item.date is a valid date string
    return moment(item.date).format("MMMM Do, YYYY");
  };

  return (
    <View>
      <Text style={styles.heading}>Upcoming Events</Text>
      <FlatList
        data={events_list}
        keyExtractor={(item) => item.id.toString()}
        showsHorizontalScrollIndicator={false}
        horizontal={true}
        renderItem={({ item, index }) => (
          <View style={styles.container}>
            <Image
              source={{ uri: `${events_images}/${item.image}` }} // Adjusted source URI
              style={styles.image}
            />
            <View style={styles.content}>
              <Text style={styles.title}>{item.title}</Text>
              <Text style={styles.date}>{formattedDate(item)}</Text>
              <Text style={styles.location}>{item.location}</Text>
            </View>

            <TouchableOpacity
              style={styles.saveButton}
              onPress={() => Alert.alert("Hello!")}>
              <Text style={styles.saveButtonText}>Save</Text>
            </TouchableOpacity>
          </View>
        )}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginRight: 10,
    padding: 10,
    backgroundColor: "white",
    borderRadius: 8,
  },
  heading: {
    fontSize: 20,
    marginBottom: 10,
  },
  image: {
    width: 160,
    height: 100,
    borderRadius: 8,
  },
  content: {
    flex: 1,
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
  },

  date: {
    fontSize: 15,
    color: "#888",
    marginTop: 6,
  },
  location: {
    fontSize: 15,
    marginTop: 6,
  },
  saveButton: {
    backgroundColor: "#3D56F0",
    padding: 10,
    borderRadius: 5,
    justifyContent: "center",
    alignItems: "center",
  },
  saveButtonText: {
    color: "white",
    fontSize: 15,
  },
});

export default Card;
