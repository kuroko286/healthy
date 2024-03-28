const userAttributes = [
  'name',
  'weight',
  'goal',
  'age',
  'height',
  'gender',
  'coefficientOfActivity',
];
export const isUserInfoComplete = (userInfo) => {
  if (!userInfo) {
    return false;
  }
  for (const attr of userAttributes) {
    if (!userInfo[attr]) {
      return false;
    }
  }
  return true;
};
