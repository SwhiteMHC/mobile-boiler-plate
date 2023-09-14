// React
import React from 'react';

// Props

// Components

// RN Components
import {SafeAreaView, ScrollView, StatusBar, View, Text} from 'react-native';

// Styles
import {styled} from 'nativewind';
const StyledView = styled(View);
const StyledScrollView = styled(ScrollView);
const StyledStatus = styled(StatusBar);

function AboutScreen(): JSX.Element {
  return (
    <SafeAreaView className="text-black dark:text-white">
      <StyledStatus className="text-black dark:text-white" />
      <StyledScrollView
        contentInsetAdjustmentBehavior="automatic"
        className="text-black dark:text-white bg-white/100">
        <StyledView className="text-black dark:text-white bg-white/100">
          <Text>About</Text>
        </StyledView>
      </StyledScrollView>
    </SafeAreaView>
  );
}

export default AboutScreen;
