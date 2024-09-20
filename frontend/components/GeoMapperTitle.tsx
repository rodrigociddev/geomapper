import React from 'react';
import { Text, StyleSheet, View } from 'react-native';

const GeoMapperTitle = () => {
  return (
    <View style={styles.titleContainer}>
      <Text style={styles.titleText}>GeoMapper</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  titleContainer: {
    alignItems: 'center', // Center the text horizontally
    justifyContent: 'center', // Center the text vertically if needed
    marginBottom: 20, // Adds space between the title and the buttons
  },
  titleText: {
    fontFamily: 'Kumbh Sans', // Replace with your desired font family
    fontStyle: 'normal',
    fontWeight: '400',
    fontSize: 96, // You can adjust this size as per your design
    color: '#FFFFFF',
    textAlign: 'center', // Ensure the text is centered
  },
});

export default GeoMapperTitle;
