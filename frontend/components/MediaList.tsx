import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator } from 'react-native';
import axiosInstance from '../api/axiosConfig';  // Import Axios instance

const MediaList = () => {
  const [mediaList, setMediaList] = useState([]);  // State to hold media list
  const [loading, setLoading] = useState(true);    // State for loading indicator
  const [error, setError] = useState(null);        // State for error handling

  // Fetch the media list from the backend when the component mounts
  useEffect(() => {
    const fetchMediaList = async () => {
      try {
        const response = await axiosInstance.get('/project/order');
        setMediaList(response.data);  // Assuming backend returns a JSON array
      } catch (err) {
        setError('Error fetching media list');
      } finally {
        setLoading(false);  // Stop loading indicator
      }
    };

    fetchMediaList();
  }, []);

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#0000ff" />
        <Text>Loading media list...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.container}>
        <Text style={styles.error}>{error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Media List</Text>
      {mediaList.length > 0 ? (
        <FlatList
          data={mediaList}
          keyExtractor={(item, index) => index.toString()}  // Use index as key if no unique ID
          renderItem={({ item }) => (
            <View style={styles.mediaItem}>
              <Text style={styles.mediaName}>Media Name: {item.name}</Text>
              <Text>Latitude: {item.latitude}</Text>
              <Text>Longitude: {item.longitude}</Text>
            </View>
          )}
        />
      ) : (
        <Text>No media found.</Text>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  mediaItem: {
    marginBottom: 15,
    padding: 10,
    backgroundColor: '#f0f0f0',
    borderRadius: 10,
  },
  mediaName: {
    fontWeight: 'bold',
  },
  error: {
    color: 'red',
    textAlign: 'center',
  },
});

export default MediaList;
