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

// BAC
import {
  useBatteryRead,
  useSerialNumberRead,
  useCountDown,
  useCountRead,
  useStartBlowing,
  useTrackBlowing,
  useAnalyzing,
  useResult,
  useErrors,
  BACConnectNearestButton,
  BACDisconnectButton,
  BACGetInfo,
  BACInitButton,
  BACStartTestButton,
} from 'bac-rn-module';

function TestScreen(): JSX.Element {
  let serialNumber = useSerialNumberRead();
  let batteryValues = useBatteryRead();
  let count = useCountRead();
  let countdown = useCountDown();
  let startBlowing = useStartBlowing();
  let blowVolume = useTrackBlowing();
  let analyzing = useAnalyzing();
  let result = useResult();
  let errors = useErrors();

  return (
    <SafeAreaView className="text-black dark:text-white">
      <StyledStatus className="text-black dark:text-white" />
      <StyledScrollView
        contentInsetAdjustmentBehavior="automatic"
        className="text-black dark:text-white bg-white/100">
        <StyledView className="text-black dark:text-white bg-white/100">
          <BACInitButton />
          <BACConnectNearestButton />
          <BACGetInfo />
          <BACStartTestButton />
          <BACDisconnectButton />

          {serialNumber ? <Text>Serial Number: {serialNumber}</Text> : null}
          {batteryValues[0] ? (
            <Text>
              Battery Level: {batteryValues[0]}, Voltage: {batteryValues[1]}
            </Text>
          ) : null}
          {count ? <Text>Device Use Count: {count}</Text> : null}
          {countdown ? <Text>Countdown to test: {countdown}</Text> : null}
          {startBlowing ? <Text>Blow!</Text> : null}
          {blowVolume ? <Text>Keep Blowing! {blowVolume}%</Text> : null}
          {analyzing ? <Text>Analyzing...</Text> : null}
          {result ? <Text>Results: {result}</Text> : null}
          {errors.length !== 0
            ? errors.map(elem => <Text key={elem}>{elem}</Text>)
            : null}
        </StyledView>
      </StyledScrollView>
    </SafeAreaView>
  );
}

export default TestScreen;
