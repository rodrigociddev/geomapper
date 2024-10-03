import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

const TopBarGeoTagR = () => {
  return (
    <View style={styles.topBar}>
      {/* Left Side: Title "GeoTagR" */}
      <View style={styles.leftContainer}>
        <Text style={styles.titleText}>GeoTagR</Text>
      </View>

      {/* Right Side: "File", "Delete Selected", "Export" buttons */}
      <View style={styles.rightContainer}>
        <TouchableOpacity style={styles.menuButton} onPress={() => console.log('File button pressed')}>
          <Text style={styles.menuText}>File</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.menuButton} onPress={() => console.log('Delete Selected pressed')}>
          <Text style={styles.menuText}>Delete Selected</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.menuButton} onPress={() => console.log('Export button pressed')}>
          <Text style={styles.menuText}>Export</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  topBar: {
    width: '100%',
    height: 60, // Adjusted for better spacing
    backgroundColor: '#001F5A', // Solid background color
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    borderBottomWidth: 1,
    borderBottomColor: '#000',
  },
  leftContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  titleText: {
    fontSize: 24, // Adjusted font size
    fontWeight: 'bold',
    color: '#FFFFFF',
  },
  rightContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  menuButton: {
    marginLeft: 20, // Spacing between buttons
  },
  menuText: {
    color: '#FFFFFF',
    fontSize: 16,
  },
});

export default TopBarGeoTagR;
