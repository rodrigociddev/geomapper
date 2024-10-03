// SidePanel.tsx
import React from 'react';
import { View, TouchableOpacity, Text, StyleSheet } from 'react-native';

const SidePanel = () => {
  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.addButton}>
        <Text style={styles.addButtonText}>+ Add Images</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    width: 308, // The width as per your CSS
    height: 1449, // The height as per your CSS
    borderWidth: 3, // Border width as per your CSS
    borderColor: '#797979', // Border color as per your CSS
    backgroundColor: '#ffffff', // Background color as per your CSS
    justifyContent: 'center', // Center the content vertically
    alignItems: 'center', // Center the content horizontally
  },
  addButton: {
    backgroundColor: '#001F5A', // Color of the button
    padding: 15, // Padding for the button
    borderRadius: 10, // Rounded corners for the button
  },
  addButtonText: {
    color: '#FFFFFF', // Text color of the button
    fontSize: 16, // Text size
  },
});

export default SidePanel;
