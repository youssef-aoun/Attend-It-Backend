import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  TextInput,
} from "react-native";
import React from "react";
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import { createStackNavigator } from "@react-navigation/stack";

const Stack = createStackNavigator();

export default function Header({ navigation }) {
  return (
    <View style={styles.container}>
      <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <MaterialIcons name="menu" size={24} color="black" />
        </TouchableOpacity>

        <View style={styles.searchContainer}>
          <MaterialIcons name="search" size={24} color="black" />
          <TextInput placeholder="| Search" style={styles.searchInput} />
        </View>

        <View>
          <MaterialIcons name="notifications" color="black" size={24} />
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    marginTop: 25,
    paddingHorizontal: 20,
    paddingTop: 20,
    backgroundColor: "#3D56F0",
    borderBottomLeftRadius: 25,
    borderBottomRightRadius: 25,
    paddingBottom: 20,
  },
  searchContainer: {
    flexDirection: "row",
    alignItems: "center",
    backgroundColor: "#f0f0f0",
    borderRadius: 5,
    paddingHorizontal: 7,
    paddingVertical: 0,
    width: 220,
  },
  searchInput: {
    flex: 1,
    paddingHorizontal: 10,
  },
});
