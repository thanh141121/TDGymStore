package net.gymsrote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.config.login.UserDetailsImpl;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.advice.exception.UnknownException;
import net.gymsrote.controller.payload.request.CreateAddressRequest;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.UserAddressDTO;
import net.gymsrote.entity.address.District;
import net.gymsrote.entity.address.Province;
import net.gymsrote.entity.address.Ward;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserAddress;
import net.gymsrote.repository.AddressDistrictRepo;
import net.gymsrote.repository.AddressProvinceRepo;
import net.gymsrote.repository.AddressWardRepo;
import net.gymsrote.repository.UserAddressRepo;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.utils.ServiceUtils;

@Service
public class AddressService {
	@Autowired
	private AddressDistrictRepo addressDistrictRepo;

	@Autowired
	private AddressProvinceRepo addressProvinceRepo;
	
	@Autowired
	AddressWardRepo addressWardRepo ;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private UserAddressRepo userAddressRepo;

	@Autowired
	ServiceUtils serviceUtils;
	
	public ListResponse<UserAddressDTO> getAllAddressByUser(Long id) {
		User u = userRepo.findById(id)
				.orElseThrow(() -> new InvalidInputDataException("Không tìm thấy 'User' với id: "+id));
		List<UserAddress> addresses = u.getUserAddress();
		return serviceUtils.convertToListResponse(addresses, UserAddressDTO.class);
	}
	
	public DataResponse<UserAddressDTO> get(Long id) {
        UserAddress address = userAddressRepo.findById(id).orElseThrow(
                () -> new InvalidInputDataException("Không tìm thấy 'Address' với id: " + id));

		return serviceUtils.convertToDataResponse(address, UserAddressDTO.class);
	}
	
	@Transactional(rollbackFor = { UnknownException.class, InvalidInputDataException.class  }) 
	public DataResponse<UserAddressDTO> create(Long idUser, CreateAddressRequest addressRequest, Long idAddress) {
		User u = userRepo.findById(idUser)
				.orElseThrow(() -> new InvalidInputDataException("Không tìm thấy 'User' với id: "+ idUser));
		Ward w;
		w = getWard(addressRequest);
		UserAddress userAdress;
		if(idAddress != null) {
			userAdress = userAddressRepo.findById(idAddress).orElseThrow( () -> 
				new InvalidInputDataException("Không tìm thấy địa chỉ với id: " + idAddress)
			);
		}else {
			userAdress = new UserAddress();
			userAdress.setUser(u);
		}
		userAdress.setAddressDetail(addressRequest.getAddressDetail());
		userAdress.setReceiverName(addressRequest.getReceiverName());
		userAdress.setReceiverPhone(addressRequest.getReceiverPhone());
		userAdress.setWard(w);
		userAddressRepo.save(userAdress);
		if(u.getDefaultAddress()== null || addressRequest.getIsDefault()) {
			u.setDefaultAddress(userAdress);
		}
		return serviceUtils.convertToDataResponse(userAdress, UserAddressDTO.class);
	}
	
	@Transactional 
	public DataResponse<UserAddressDTO> setDefault(Long idUser, Long idAddress) {
		User u = userRepo.findById(idUser)
				.orElseThrow(() -> new InvalidInputDataException("Không tìm thấy 'User' với id: "+ idUser));

		UserAddress userAdress = userAddressRepo.findById(idAddress).orElseThrow( () -> 
				new InvalidInputDataException("Không tìm thấy địa chỉ với id: " + idAddress)
			);
	
		u.setDefaultAddress(userAdress);
	
		return serviceUtils.convertToDataResponse(userAdress, UserAddressDTO.class);
	}
	

	@Transactional
	private Ward getWard(CreateAddressRequest addressRequest) {
		try {
			if(!addressWardRepo.existsById(addressRequest.getWardCode())){
				Ward w =  new Ward(addressRequest.getWardCode(), addressRequest.getWardName().trim().toString());
				if(!addressDistrictRepo.existsById(addressRequest.getDistrictID())) {
					District d  = new District(addressRequest.getDistrictID(), addressRequest.getDistrictName());
					w.setDistrict(d);
					if(!addressProvinceRepo.existsById(addressRequest.getProvinceID())) {
						Province p = new Province(addressRequest.getProvinceID(), addressRequest.getProvinceName());
						d.setProvince(p);
						addressProvinceRepo.save(p);
					}else{
						d.setProvince(addressProvinceRepo.getReferenceById(addressRequest.getProvinceID()));
					}
					addressDistrictRepo.save(d);
				}else{
					w.setDistrict(addressDistrictRepo.getReferenceById(addressRequest.getDistrictID()));
				}
				return addressWardRepo.save(w);
			}else {
				return addressWardRepo.findByWardCode(addressRequest.getWardCode());
			}
		}catch(Exception e) {
			throw new UnknownException(e.getMessage());
		}
	}
}
