import pageIllustrations from '../../assets/pageIllustrations.svg';
import { AuthorizationImg } from '../../components/AuthorizationImg/AuthorizationImg';
import {
  BodyTextWrapper,
  ForgotPassLink,
  SignInSection,
} from './SignInPage.styled';
import { TextPartWrapper } from './SignInPage.styled';
import { Title } from '../../components/Title/Title';
import { SubTitle } from '../../components/SubTitle/SubTitle';
import { SingInForm } from '../../components/SignInForm/SingInForm';
import { SignInOrUp } from '../../components/SignInOrUp/SignInOrUp';
import { GOOGLE_AUTH_URL } from '../../constants/url';

export default function SignInPage() {
  return (
    <SignInSection>
      <AuthorizationImg pageIllustrations={pageIllustrations} />
      <TextPartWrapper>
        <BodyTextWrapper>
          <Title titleText={'Sign in'} />
          <SubTitle description={'You need to login to use the service'} />
          <SingInForm />
          <ForgotPassLink to="/forgot-password">
            Forgot your password huuusdsduh?
          </ForgotPassLink>
          <div className="social-login">
            <a
              className="btn btn-block social-btn google"
              href={GOOGLE_AUTH_URL}
            >
              Log in with Google
            </a>
          </div>
        </BodyTextWrapper>
        <SignInOrUp
          descriptionText={`If you don't have an account yet`}
          linkText={'Sign up'}
          to={'/signup'}
        />
      </TextPartWrapper>
    </SignInSection>
  );
}
