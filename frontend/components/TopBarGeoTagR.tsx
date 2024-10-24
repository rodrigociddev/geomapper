import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

const TopBarGeoTagR = () => {
  const [fileDropdownVisible, setFileDropdownVisible] = useState(false);
  const [editDropdownVisible, setEditDropdownVisible] = useState(false);
  const [exportDropdownVisible, setExportDropdownVisible] = useState(false);

  const toggleFileDropdown = () => {
    setFileDropdownVisible(!fileDropdownVisible);
    setExportDropdownVisible(false);
    setEditDropdownVisible(false);
  };
  const toggleEditDropdown = () => {
    setEditDropdownVisible(!editDropdownVisible);
    setFileDropdownVisible(false);
    setExportDropdownVisible(false);
  }
  const toggleExportDropdown = () => {
    setExportDropdownVisible(!exportDropdownVisible);
    setFileDropdownVisible(false);
    setEditDropdownVisible(false);
  };

  return (
    <View style={styles.topBar}>
      <View style={styles.leftContainer}>
        <Text style={styles.titleText}>GeoTagR</Text>
      </View>

      <View style={styles.rightContainer}>
        <View>
          <TouchableOpacity style={styles.menuButton} onPress={toggleFileDropdown}>
            <Text style={styles.menuText}>File</Text>
          </TouchableOpacity>
          {fileDropdownVisible && (
            <View style={styles.dropdownMenu}>
              <TouchableOpacity onPress={() => console.log('Add Media pressed')}>
                <Text style={styles.dropdownItem}>Add Media</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={() => console.log('Save Project pressed')}>
                <Text style={styles.dropdownItem}>Save Project</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={() => console.log('Open Project pressed')}>
                <Text style={styles.dropdownItem}>Open Project</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={() => console.log('New Project pressed')}>
                <Text style={styles.dropdownItem}>New Project</Text>
              </TouchableOpacity>
            </View>
          )}
        </View>

        <View>
          <TouchableOpacity style={styles.menuButton} onPress={toggleEditDropdown}>
            <Text style={styles.menuText}>Edit</Text>
          </TouchableOpacity>
          {editDropdownVisible && (
            <View style={styles.dropdownMenu}>
              <TouchableOpacity onPress={() => console.log('Select All pressed')}>
                <Text style={styles.dropdownItem}>Select All</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={() => console.log('Unselect All pressed')}>
                <Text style={styles.dropdownItem}>Unselect All</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={() => console.log('Delete Selected pressed')}>
                <Text style={styles.dropdownItem}>Delete Selected</Text>
              </TouchableOpacity>
            </View>
          )}
        </View>


        <View>
          <TouchableOpacity style={styles.menuButton} onPress={toggleExportDropdown}>
            <Text style={styles.menuText}>Export</Text>
          </TouchableOpacity>
          {exportDropdownVisible && (
            <View style={styles.dropdownMenu}>
              <TouchableOpacity onPress={() => console.log('Export as KML pressed')}>
                <Text style={styles.dropdownItem}>Export as KML</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={() => console.log('Export as KMZ pressed')}>
                <Text style={styles.dropdownItem}>Export as KMZ</Text>
              </TouchableOpacity>
            </View>
          )}
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  topBar: {
    width: '100%',
    height: 60,
    backgroundColor: '#001F5A',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 10,
  },
  leftContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  titleText: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#FFFFFF',
  },
  rightContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  menuButton: {
  },
  menuText: {
    width: 100,
    height: 60,
    color: '#FFFFFF',
    fontSize: 15,
    paddingVertical: 15,
    borderWidth: 1,
    borderColor: '#505050',
  },
  dropdownMenu: {
    position: 'absolute',
    top: 60,
    width: 100,
    right: 0,
    backgroundColor: '#FFFFFF',
    elevation: 0,
    zIndex: 150,
  },
  dropdownItem: {
    padding: 10,
    fontSize: 16,
    color: '#000',
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
});

export default TopBarGeoTagR;
