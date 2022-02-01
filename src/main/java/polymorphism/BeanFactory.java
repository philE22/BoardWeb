package polymorphism;

public class BeanFactory {
	public Object getbean(String beanName) {
		if(beanName.equals("samsung")){
			return new SamsungTV();
		} else if (beanName.equals("lg")) {
			return new LgTV();
		}
		return null;
	}
}
