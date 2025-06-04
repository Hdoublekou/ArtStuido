package com.artstudio.backend.controller;

import com.artstudio.backend.model.Address;
import com.artstudio.backend.repository.AddressRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // 查询当前用户所有地址
    @GetMapping("/user/{userId}")
    public List<Address> getUserAddresses(@PathVariable Long userId) {
        return addressRepository.findByUserId(userId);
    }

    // 新增地址
    @PostMapping
    public Address addAddress(@RequestBody Address address) {
        return addressRepository.save(address);
    }

    // 编辑地址
    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address updated) {
        Optional<Address> opt = addressRepository.findById(id);
        if (opt.isPresent()) {
            Address addr = opt.get();
            addr.setRecipient(updated.getRecipient());
            addr.setPostcode(updated.getPostcode());
            addr.setPrefecture(updated.getPrefecture());
            addr.setCity(updated.getCity());
            addr.setAddress(updated.getAddress());
            addr.setPhone(updated.getPhone());
            return addressRepository.save(addr);
        }
        throw new RuntimeException("Address not found");
    }

    // 删除地址
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
    }
}
