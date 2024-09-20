import React from 'react';
import { View, Text, StyleSheet, Image, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons.js';

const TopBar = () => {
  return (
    <View style={styles.topBar}>
      {/* Left Side: Globe Icon and "GeoMapper" Text */}
      <View style={styles.leftContainer}>
        <Image
          source={require('../assets/Globe.png')} // Globe icon
          style={styles.globeIcon}
        />
        <Text style={styles.titleText}>GeoMapper</Text>
      </View>

      {/* Right Side: Minimize, Maximize, and Exit buttons */}
      <View style={styles.rightContainer}>

<TouchableOpacity style={styles.iconButton}>
  <Icon name="minimize" size={18} color="#FFFFFF" />
</TouchableOpacity>
<TouchableOpacity style={styles.iconButton}>
  <Icon name="fullscreen" size={18} color="#FFFFFF" />
</TouchableOpacity>
<TouchableOpacity style={styles.iconButton}>
  <Icon name="close" size={18} color="#FFFFFF" />
</TouchableOpacity>

      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  topBar: {
    width: '100%',
    height: 40,
    backgroundColor: '#001F5A', // Use a solid background color instead of a gradient
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#000',
  },
  leftContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  globeIcon: {
    width: 25,
    height: 25,
    marginRight: 10,
  },
  titleText: {
    fontFamily: 'Kumbh Sans', // You can adjust the font as per your need
    fontSize: 18,
    color: '#FFFFFF',
  },
  rightContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  iconButton: {
    marginLeft: 15,
    padding: 5,
  },
  iconText: {
    fontSize: 18,
    color: '#FFFFFF',
  },
});

export default TopBar;
