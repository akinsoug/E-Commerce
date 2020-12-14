package com.tts.ECommerce.controller;

import java.util.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
//import com.stripe.model.Charge;
import com.tts.ECommerce.model.ChargeRequest;
import com.tts.ECommerce.service.StripeService;
import com.tts.ECommerce.service.UserService;

/**
 * Test the Stripe API with the fake card: 4242 4242 4242 4242 exp: 01/22 CVC: 111
 * @author Esther2
 *
 */

@Controller
public class CheckoutController {
	 @Autowired
     UserService userService;

    @Value("${STRIPE_PUBLIC_KEY}") 
    private String stripePublicKey;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("amount", 50 * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "ecommerce/checkout";
    }
    
    @Controller
    public class ChargeController {

        @Autowired
        private StripeService paymentsService;

        @PostMapping("/charge")
        public String charge(ChargeRequest chargeRequest, Model model)
          throws StripeException {
            chargeRequest.setDescription("Example charge");
            chargeRequest.setCurrency(ChargeRequest.Currency.USD);
            Charge charge = paymentsService.charge(chargeRequest);
            model.addAttribute("id", charge.getId());
            model.addAttribute("status", charge.getStatus());
            model.addAttribute("chargeId", charge.getId());
            model.addAttribute("balance_transaction", charge.getBalanceTransaction());

            //put this at the end of the post request just before the return.
            userService.getLoggedInUser().getCart().clear();
            
            return "ecommerce/result";
        }

        @ExceptionHandler(StripeException.class)
        public String handleError(Model model, StripeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "ecommerce/result";
        }
    }
}