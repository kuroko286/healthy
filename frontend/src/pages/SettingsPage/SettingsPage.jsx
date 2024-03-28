import * as Yup from 'yup';

import {
  Avatar,
  AvatarIcon,
  ButtonWraper,
  ButtonWraperDown,
  CancelBtn,
  CustomRadio,
  FileInput,
  FormInputs,
  GenderRadios,
  IconWrapper,
  ImageInput,
  Label,
  LabelImg,
  RadioField,
  RadioLabel,
  SaveBtn,
  TextInput,
  Title,
  TitleContainer,
  UploadIcon,
  UploadWrap,
  WrapImgAndForm,
  YourActivityInput,
} from './SettingsPage.styled';
import Illustration from '../../assets/pageIllustrations.svg';
import { Formik, Form } from 'formik';
import icons from '../../assets/icons.svg';
import { useDispatch, useSelector } from 'react-redux';
import { selectUserData, selectUserId } from '../../redux/selectors';
import {
  addUserAvatar,
  getCurrentUser,
  updateUserInformation,
} from '../../redux/operations';
import { useState, useEffect } from 'react';
import { CoefficientOfActivity, Gender } from '../../constants/user';

export default function SettingsPage() {
  const { info: user } = useSelector(selectUserData);
  const dispatch = useDispatch();
  const userId = useSelector(selectUserId);

  const initialValues = {
    age: user && user.age !== undefined ? user.age : '',
    weight: user && user.weight !== undefined ? user.weight : '',
    height: user && user.height !== undefined ? user.height : '',
    gender: user && user.gender !== undefined ? user.gender : '',
    coefficientOfActivity:
      user && user.coefficientOfActivity !== undefined
        ? String(user.coefficientOfActivity)
        : '',
    name: user && user.name !== undefined ? user.name : '',
  };

  const [avatar, setAvatar] = useState(user ? user.avatarURL : '');
  const [createUrl, setObjectURL] = useState(false);

  const handleSave = (values) => {
    const updatedValues = { ...values };
    updatedValues.coefficientOfActivity = Number(
      updatedValues.coefficientOfActivity
    );

    if (JSON.stringify(initialValues) !== JSON.stringify(values)) {
      dispatch(updateUserInformation({ userId, updatedValues }));
    }

    avatar !== '' &&
      typeof avatar === 'object' &&
      avatar instanceof File &&
      dispatch(addUserAvatar({ userId, avatar }));

    setTimeout(() => {
      dispatch(getCurrentUser(userId));
      setAvatar(user.avatarURL);
      avatar !== '' && typeof avatar === 'object' && setObjectURL(false);

      window.location.href = '/main ';
    }, 2300);
  };

  const handleCancel = (resetForm) => {
    resetForm({ values: initialValues });
    setAvatar(user ? user.avatarURL : '');
    setObjectURL(false);
    window.location.href = '/healthy_hub/main ';
  };

  const validationSchema = Yup.object().shape({
    age: Yup.number()
      .positive('Must be a positive number')
      .integer('Must be an integer')
      .max(100, 'Cannot be greater than 100')
      .nullable(),
    height: Yup.number()
      .positive('Must be a positive number')
      .max(300, 'Cannot be greater than 300')
      .nullable(),
    weight: Yup.number()
      .positive('Must be a positive number')
      .max(500, 'Cannot be greater than 500')
      .nullable(),
  });

  useEffect(() => {
    if (user) {
      setAvatar(user.avatarURL);
    }
  }, [user]);

  return (
    <Formik
      initialValues={initialValues}
      enableReinitialize
      onSubmit={handleSave}
      validationSchema={validationSchema}
    >
      {({ resetForm, errors, touched }) => (
        <Form>
          <TitleContainer>
            <Title>Profile setting</Title>
            <ButtonWraper>
              <CancelBtn type="button" onClick={() => handleCancel(resetForm)}>
                Cancel
              </CancelBtn>
              <SaveBtn type="submit">Save</SaveBtn>
            </ButtonWraper>
          </TitleContainer>
          <WrapImgAndForm>
            <IconWrapper>
              <use href={`${Illustration}#icon-settings`} />
            </IconWrapper>
            <FormInputs>
              <Label htmlFor="name">
                Your name
                <TextInput type="text" id="name" name="name" />
              </Label>
              <LabelImg htmlFor="avatar">
                Your photo
                <FileInput>
                  {avatar === '' ? (
                    <AvatarIcon>
                      <use href={`${icons}#icon-profile-circle`} />
                    </AvatarIcon>
                  ) : (
                    <Avatar
                      src={
                        user.avatarURL
                          ? createUrl
                            ? URL.createObjectURL(avatar)
                            : avatar
                          : URL.createObjectURL(avatar)
                      }
                      alt="Selected"
                    />
                  )}
                  <UploadWrap>
                    <UploadIcon>
                      <use href={`${icons}#icon-direct-inbox`} />
                    </UploadIcon>
                    <ImageInput
                      type="file"
                      accept="image/*"
                      id="avatar"
                      name="avatar"
                      onChange={(e) => {
                        setAvatar(e.target.files[0]);
                        setObjectURL(true);
                      }}
                    />
                    Download new photo
                  </UploadWrap>
                </FileInput>
              </LabelImg>
              <Label htmlFor="age">
                Your age
                <TextInput
                  type="number"
                  id="age"
                  name="age"
                  className={touched.age && errors.age ? 'error' : ''}
                />
                {touched.age && errors.age && (
                  <div className="error-message">{errors.age}</div>
                )}
              </Label>
              <Label>
                Gender
                <GenderRadios>
                  <RadioLabel>
                    <RadioField
                      type="radio"
                      name="gender"
                      value={Gender.MALE}
                    />
                    Male
                    <CustomRadio>
                      <span></span>
                    </CustomRadio>
                  </RadioLabel>
                  <RadioLabel>
                    <RadioField
                      type="radio"
                      name="gender"
                      value={Gender.FEMALE}
                    />
                    Female
                    <CustomRadio>
                      <span></span>
                    </CustomRadio>
                  </RadioLabel>
                </GenderRadios>
              </Label>
              <Label htmlFor="height">
                Height
                <TextInput
                  type="number"
                  id="height"
                  name="height"
                  className={touched.height && errors.height ? 'error' : ''}
                />
                {touched.height && errors.height && (
                  <div className="error-message">{errors.height}</div>
                )}
              </Label>
              <Label htmlFor="weight">
                Weight
                <TextInput
                  type="number"
                  id="weight"
                  name="weight"
                  className={touched.weight && errors.weight ? 'error' : ''}
                />
                {touched.weight && errors.weight && (
                  <div className="error-message">{errors.weight}</div>
                )}
              </Label>
              <YourActivityInput>
                Your activity
                <RadioLabel>
                  <RadioField
                    type="radio"
                    id="coefficientOfActivity1"
                    name="coefficientOfActivity"
                    value={CoefficientOfActivity.VERY_LOW}
                  />
                  <CustomRadio>
                    <span></span>
                  </CustomRadio>
                  {CoefficientOfActivity.VERY_LOW} - if you do not have physical
                  activity and sedentary work
                </RadioLabel>
                <RadioLabel>
                  <RadioField
                    type="radio"
                    id="coefficientOfActivity2"
                    name="coefficientOfActivity"
                    value={CoefficientOfActivity.LOW}
                  />
                  <CustomRadio>
                    <span></span>
                  </CustomRadio>
                  {CoefficientOfActivity.LOW} - if you do short runs or light
                  gymnastics 1-3 times a week
                </RadioLabel>
                <RadioLabel>
                  <RadioField
                    type="radio"
                    id="coefficientOfActivity3"
                    name="coefficientOfActivity"
                    value={CoefficientOfActivity.MEDIUM}
                  />
                  <CustomRadio>
                    <span></span>
                  </CustomRadio>
                  {CoefficientOfActivity.MEDIUM} - if you play sports with
                  average loads 3-5 times a week
                </RadioLabel>
                <RadioLabel>
                  <RadioField
                    type="radio"
                    id="coefficientOfActivity4"
                    name="coefficientOfActivity"
                    value={CoefficientOfActivity.HIGH}
                  />
                  <CustomRadio>
                    <span></span>
                  </CustomRadio>
                  {CoefficientOfActivity.HIGH} - if you train fully 6-7 times a
                  week
                </RadioLabel>
                <RadioLabel>
                  <RadioField
                    type="radio"
                    id="coefficientOfActivity4"
                    name="coefficientOfActivity"
                    value={CoefficientOfActivity.VERY_HIGH}
                  />
                  <CustomRadio>
                    <span></span>
                  </CustomRadio>
                  {CoefficientOfActivity.VERY_HIGH} - if your work is related to
                  physical labor, you train 2 times a day and include strength
                  exercises in your training program
                </RadioLabel>
              </YourActivityInput>
            </FormInputs>
            <ButtonWraperDown>
              <CancelBtn
                type="button"
                onClick={() => {
                  handleCancel(resetForm);
                }}
              >
                Cancel
              </CancelBtn>
              <SaveBtn type="submit">Save</SaveBtn>
            </ButtonWraperDown>
          </WrapImgAndForm>
        </Form>
      )}
    </Formik>
  );
}
