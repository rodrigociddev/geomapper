import React from 'react';
import { Text, TouchableOpacity, StyleSheet } from 'react-native';

// Define the props type, so it expects a `label` prop
interface ButtonProps {
  label: string;
}

const Button: React.FC<ButtonProps> = ({ label }) => {
  return (
    <TouchableOpacity style={styles.button}>
      <Text style={styles.buttonText}>{label}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    width: 448,
    height: 106,
    backgroundColor: '#081E3F',
    borderColor: '#2C2C2C',
    borderWidth: 1,
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 12,
    marginBottom: 20, // Adds spacing between buttons if you have more than one
    marginHorizontal: 50
  },
  buttonText: {
    fontFamily: 'Inter',
    fontSize: 24,
    color: '#F5F5F5',
  },
});

export default Button;
