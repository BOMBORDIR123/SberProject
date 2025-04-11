import SavetheEarthIcon from "@/shared/icons/SavetheEarth";
import styles from "./styles.module.scss";
import { Button, Form, FormProps, Input, message, Skeleton } from "antd";
import PhoneInput from "antd-phone-input";
import { useLogin } from "./api/useLogin";
import { Link, useNavigate } from "react-router-dom";
import logo from "@/assets/logo.png";
import { useSelector } from "react-redux";
import { useActions } from "@/hooks/useActions";
import { useEffect } from "react";

export type FormValues = {
  phoneNumber: string;
  password: string;
};

const LoginPage = () => {
  const [form] = Form.useForm();
  const [messageApi, contextHolder] = message.useMessage();

  const { currentUser } = useSelector((state: any) => state.currentUser);
  const { setCurrentUser } = useActions();
  const navigate = useNavigate();

  console.log("currentUser", currentUser);

  const { mutate: login, isPending, isSuccess, data } = useLogin(messageApi);

  const onFinish: FormProps<FormValues>["onFinish"] = () => {
    const filteredForm = {
      phoneNumber:
        form.getFieldValue("phoneNumber").countryCode +
        form.getFieldValue("phoneNumber").areaCode +
        form.getFieldValue("phoneNumber").phoneNumber,
      password: form.getFieldValue("password").trim(),
    };
    login(filteredForm);
  };

  useEffect(() => {
    if (isSuccess) {
      setCurrentUser(data);
      navigate("/home");
    }
  }, [isSuccess, data]);

  return (
    <>
      {contextHolder}
      <div className={styles.container}>
        <SavetheEarthIcon />
        <div className={styles.form}>
          <div className={styles.logo}>
            <h2 className={styles.form__subtitle}>Зеленый Ростов</h2>
            <img src={logo} alt="logo" className={styles.logo__image} />
          </div>
          <h1 className={styles.form__welcome}>
            Добро пожаловать в Зеленый Ростов!
          </h1>
          <h1 className={styles.form__welcome_mobile}>Добро пожаловать!</h1>
          {isPending ? (
            <Skeleton paragraph={{ rows: 8 }} />
          ) : (
            <Form
              form={form}
              name="basic"
              initialValues={{ remember: true }}
              onFinish={onFinish}
              // onFinishFailed={onFinishFailed}
              autoComplete="off"
              layout="vertical"
              className={styles.form__basic}
            >
              <Form.Item
                label="Номер телефона"
                name="phoneNumber"
                rules={[
                  {
                    required: true,
                    message: "Пожалуйста, введите номер телефона",
                  },
                ]}
                className={styles.form__item}
              >
                <PhoneInput enableSearch country="ru" />
              </Form.Item>
              <Form.Item
                label="Пароль"
                name="password"
                rules={[
                  {
                    required: true,
                    message: "Пожалуйста, введите пароль от 8 до 60 символов",
                    min: 8,
                    max: 60,
                  },
                ]}
                className={styles.form__item}
              >
                <Input placeholder="qwerty123" width="100%" />
              </Form.Item>
              <Form.Item className={styles.form__send}>
                <Button type="link" htmlType="submit">
                  Войти
                </Button>
              </Form.Item>
              <div className={styles.form__login}>
                <div className={styles.form__login_line}></div>
                <p>или</p>
                <div className={styles.form__login_line}></div>
              </div>
              <div className={styles.form__login_wrapper}>
                <Link to="/registration" className={styles.form__loginButton}>
                  Создать аккаунт
                </Link>
              </div>
            </Form>
          )}
        </div>
      </div>
    </>
  );
};

export default LoginPage;
