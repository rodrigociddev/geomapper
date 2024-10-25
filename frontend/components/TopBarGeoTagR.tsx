import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import axios from 'axios';

interface TopBarGeoTagRProps {
  onSelectAll: () => void;
  onDeselectAll: () => void;
  onDeleteSelected: () => void;
}

const TopBarGeoTagR: React.FC<TopBarGeoTagRProps> = ({ onSelectAll, onDeselectAll, onDeleteSelected }) => {
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
  };

  const toggleExportDropdown = () => {
    setExportDropdownVisible(!exportDropdownVisible);
    setFileDropdownVisible(false);
    setEditDropdownVisible(false);
  };

  // Axios request to export KML
const exportKML = async () => {
  try {
    const response = await axios.post('http://localhost:8080/export', null, {
      params: {
        format: 'KML', // Send as a string, backend will handle conversion
        fileName: 'exported_file', 
      },
    });
    console.log('KML exported successfully:', response.data);
  } catch (error) {
    console.error('Error exporting KML:', error);
  }
};

// Axios request to export KMZ
const exportKMZ = async () => {
  try {
    const response = await axios.post('http://localhost:8080/export', null, {
      params: {
        format: 'KMZ', // Send as a string, backend will handle conversion
        fileName: 'exported_file', 
      },
    });
    console.log('KMZ exported successfully:', response.data);
  } catch (error) {
    console.error('Error exporting KMZ:', error);
  }
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
              <TouchableOpacity>
                <Text style={styles.dropdownItem}>Add Media</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.dropdownItem}>Save Project</Text>
              </TouchableOpacity>
              <TouchableOpacity>
                <Text style={styles.dropdownItem}>Open Project</Text>
              </TouchableOpacity>
              <TouchableOpacity>
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
              <TouchableOpacity onPress={onSelectAll}>
                <Text style={styles.dropdownItem}>Select All</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={onDeselectAll}>
                <Text style={styles.dropdownItem}>Unselect All</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={onDeleteSelected}>
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
              <TouchableOpacity onPress={exportKML}>
                <Text style={styles.dropdownItem}>Export as KML</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={exportKMZ}>
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
    zIndex: 1,
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
    zIndex: 2,
    overflow: 'visible',
  },
  menuButton: {},
  menuText: {
    width: 100,
    height: 60,
    color: '#FFFFFF',
    fontSize: 15,
    paddingVertical: 15,
  },
  dropdownMenu: {
    position: 'absolute',
    top: 60,
    width: 100,
    right: 0,
    backgroundColor: '#FFFFFF',
    zIndex: 999,
    elevation: 5,
  },
  dropdownItem: {
    padding: 10,
    fontSize: 12,
    color: '#000',
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
});

export default TopBarGeoTagR;
