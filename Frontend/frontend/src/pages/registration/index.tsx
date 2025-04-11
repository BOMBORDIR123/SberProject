import SavetheEarthIcon from "@/shared/icons/SavetheEarth";
import styles from "./styles.module.scss";
import { Button, Form, FormProps, Input, message, Skeleton } from "antd";
import PhoneInput from "antd-phone-input";
import { useRegistration } from "./api/useRegistration";
import { Link, useNavigate } from "react-router-dom";
import logo from "@/assets/logo.png";
import { useEffect } from "react";

export type FormValues = {
  phoneNumber: string;
  firstName: string;
  lastName: string;
  password: string;
  confirmPassword: string;
};

const RegistrationPage = () => {
  const [form] = Form.useForm();
  const [messageApi, contextHolder] = message.useMessage();
  const navigate = useNavigate();

  let {
    mutate: registration,
    isPending,
    isSuccess,
  } = useRegistration(messageApi);

  const onFinish: FormProps<FormValues>["onFinish"] = () => {
    const filteredForm = {
      phoneNumber:
        form.getFieldValue("phoneNumber").countryCode +
        form.getFieldValue("phoneNumber").areaCode +
        form.getFieldValue("phoneNumber").phoneNumber,
      firstName: form.getFieldValue("firstName").trim(),
      lastName: form.getFieldValue("lastName").trim(),
      password: form.getFieldValue("password").trim(),
      confirmPassword: form.getFieldValue("confirmPassword").trim(),
    };
    registration(filteredForm);
  };

  useEffect(() => {
    if (isSuccess) {
      setTimeout(() => {
        navigate("/login");
      }, 2000);
    }
  }, [isSuccess]);

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
          {isPending || isSuccess ? (
            <Skeleton paragraph={{ rows: 13 }} />
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
                label="Имя"
                name="firstName"
                rules={[
                  {
                    required: true,
                    message: "Пожалуйста, введите имя от 2 до 60 символов",
                    min: 2,
                    max: 60,
                  },
                  {
                    pattern: /^[a-zA-Zа-яА-ЯёЁ]+$/,
                    message: "Имя должно содержать только буквы",
                  },
                ]}
                className={styles.form__item}
              >
                <Input placeholder="Дмитрий" width="100%" />
              </Form.Item>
              <Form.Item
                label="Фамилия"
                name="lastName"
                rules={[
                  {
                    required: true,
                    message: "Пожалуйста, введите имя от 2 до 60 символов",
                    min: 2,
                    max: 60,
                  },
                  {
                    pattern: /^[a-zA-Zа-яА-ЯёЁ]+$/,
                    message: "Имя должно содержать только буквы",
                  },
                ]}
                className={styles.form__item}
              >
                <Input placeholder="Попов" width="100%" />
              </Form.Item>
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
              <Form.Item
                label="Повторить пароль"
                name="confirmPassword"
                dependencies={["password"]}
                rules={[
                  {
                    required: true,
                    message: "Пожалуйста, повторите пароль от 8 до 60 символов",
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
                  Создать
                </Button>
              </Form.Item>
              <div className={styles.form__login}>
                <div className={styles.form__login_line}></div>
                <p>или</p>
                <div className={styles.form__login_line}></div>
              </div>
              <div className={styles.form__login_wrapper}>
                <Link to="/login" className={styles.form__loginButton}>
                  Войти
                </Link>
              </div>
            </Form>
          )}
        </div>
      </div>
    </>
  );
};

export default RegistrationPage;
